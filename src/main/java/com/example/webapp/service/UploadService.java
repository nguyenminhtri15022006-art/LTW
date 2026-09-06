package com.example.webapp.service;

import com.example.webapp.config.Environment;

import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class UploadService {
    public static final long MAX_SIZE = 5 * 1024 * 1024;

    public Path directory() {
        return Path.of(
                        Environment.get(
                                "UPLOAD_DIR",
                                Path.of(
                                                System.getProperty("java.io.tmpdir"),
                                                "BT25-08-2026-uploads")
                                        .toString()))
                .toAbsolutePath()
                .normalize();
    }

    public String save(Part part) throws IOException {
        if (part == null
                || part.getSubmittedFileName() == null
                || part.getSubmittedFileName().isBlank()) return null;
        if (part.getSize() == 0) throw new ValidationException("image", "File ảnh rỗng.");
        if (part.getSize() > MAX_SIZE) throw new ValidationException("image", "Ảnh tối đa 5 MB.");
        String client = part.getSubmittedFileName();
        int dot = client.lastIndexOf('.');
        String ext = dot < 0 ? "" : client.substring(dot + 1).toLowerCase(Locale.ROOT);
        if (!Set.of("jpg", "jpeg", "png", "gif", "webp").contains(ext))
            throw new ValidationException("image", "Chỉ chấp nhận jpg/jpeg/png/webp/gif.");
        byte[] bytes;
        try (InputStream in = part.getInputStream()) {
            bytes = in.readNBytes((int) MAX_SIZE + 1);
        }
        if (bytes.length > MAX_SIZE) throw new ValidationException("image", "Ảnh tối đa 5 MB.");
        String actual = detect(bytes);
        if (actual == null || !(actual.equals(ext) || actual.equals("jpg") && ext.equals("jpeg")))
            throw new ValidationException("image", "Nội dung file không khớp định dạng ảnh.");
        validateContent(bytes, actual);
        Files.createDirectories(directory());
        String name = UUID.randomUUID() + "." + actual;
        Files.write(directory().resolve(name), bytes, StandardOpenOption.CREATE_NEW);
        return name;
    }

    private String detect(byte[] b) {
        if (b.length < 12) return null;
        if ((b[0] & 255) == 255 && (b[1] & 255) == 216 && (b[2] & 255) == 255) return "jpg";
        if (Arrays.equals(Arrays.copyOf(b, 8), new byte[] {(byte) 137, 80, 78, 71, 13, 10, 26, 10}))
            return "png";
        String first = new String(b, 0, 12, StandardCharsets.US_ASCII);
        if (first.startsWith("GIF87a") || first.startsWith("GIF89a")) return "gif";
        if (first.startsWith("RIFF") && first.substring(8).equals("WEBP")) return "webp";
        return null;
    }

    private void validateContent(byte[] bytes, String format) {
        if ("webp".equals(format)) {
            if (bytes.length < 20)
                throw new ValidationException("image", "File WEBP không hợp lệ.");
            long size =
                    Integer.toUnsignedLong(
                            java.nio.ByteBuffer.wrap(bytes, 4, 4)
                                    .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                                    .getInt());
            String chunk = new String(bytes, 12, 4, StandardCharsets.US_ASCII);
            if (size + 8 != bytes.length || !Set.of("VP8 ", "VP8L", "VP8X").contains(chunk))
                throw new ValidationException("image", "File WEBP không hợp lệ.");
            return;
        }
        try (var stream =
                javax.imageio.ImageIO.createImageInputStream(new ByteArrayInputStream(bytes))) {
            var readers = javax.imageio.ImageIO.getImageReaders(stream);
            if (!readers.hasNext()) throw new IOException("Unsupported image");
            var reader = readers.next();
            try {
                reader.setInput(stream);
                long width = reader.getWidth(0), height = reader.getHeight(0);
                if (width < 1 || height < 1 || width * height > 25000000)
                    throw new ValidationException("image", "Ảnh tối đa 25 triệu pixel.");
                if (reader.read(0) == null) throw new IOException("Invalid image");
            } finally {
                reader.dispose();
            }
        } catch (IOException e) {
            throw new ValidationException("image", "File ảnh bị hỏng hoặc không đọc được.");
        }
    }

    public Path resolve(String name) {
        if (name == null || !name.matches("[a-f0-9-]{36}\\.(jpg|png|gif|webp)")) return null;
        Path file = directory().resolve(name).normalize();
        return file.startsWith(directory()) ? file : null;
    }

    public void discard(String name) {
        Path path = resolve(name);
        if (path != null)
            try {
                Files.deleteIfExists(path);
            } catch (IOException ignored) {
            }
    }
}
