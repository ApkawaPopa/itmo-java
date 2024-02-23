package md2html;

import java.io.*;
import java.util.*;

public class Md2Html {
    public static void main(String files[]) {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(files[0]),
                    "utf-8"
                )
            );
            BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(files[1]),
                    "utf-8"
                )
            )
        ) {
            String[] mark = new String[]{"*", "_", "**", "__", "--", "`", "~"};
            String[] remade = new String[]{
                 "<em>",  "<em>",  "<strong>",  "<strong>",  "<s>",  "<code>", "<mark>",
                "</em>", "</em>", "</strong>", "</strong>", "</s>", "</code>", "</mark>"
            };
            byte bad = (byte) 0xff;
            int[] stack = new int[mark.length * 2];
            int sz;
            String[] special = new String[]{"<", ">", "&", "&lt;", "&gt;", "&amp;"};
            String line = in.readLine();
            while (line != null) {
                while (line != null && line.length() == 0) {
                    line = in.readLine();
                }
                if (line == null) {
                    break;
                }
                StringBuilder sb = new StringBuilder();
                while (line != null && line.length() != 0) {
                    sb.append(line + "\n");
                    line = in.readLine();
                }
                sb.setLength(sb.length() - 1);
                int header = 0;
                while (header < sb.length() && sb.charAt(header) == '#') {
                    header++;
                }
                if (header == sb.length() || sb.charAt(header) != ' ' || header == 0) {
                    header = -1;
                }
                sz = 0;
                boolean[] was = new boolean[mark.length];
                byte[] remake = new byte[sb.length()];
                Arrays.fill(remake, bad);
                for (int i = header + 1; i < sb.length(); i++) {
                    char c = sb.charAt(i);
                    if (c == '\\') {
                        i++;
                        continue;
                    }
                    byte pos = bad;
                    if (i + 1 < sb.length()) {
                        pos = find(mark, String.valueOf(c) + sb.charAt(i + 1));
                    }
                    if (pos == bad) {
                        pos = find(mark, String.valueOf(c));
                    }
                    if (pos != bad) {
                        if (was[pos]) {
                            int j = -1;
                            byte prevPos = bad;
                            while (prevPos != pos) {
                                j = stack[--sz];
                                prevPos = (byte) stack[--sz];
                                was[prevPos] = false;
                            }
                            remake[j] = pos;
                            remake[i] = (byte) (pos + mark.length);
                        } else {
                            stack[sz++] = pos;
                            stack[sz++] = i;
                            was[pos] = true;
                        }
                        if (mark[pos].length() == 2) {
                            i++;
                        }
                    }
                }
                if (header != -1) {
                    out.write("<h" + header + ">");
                } else {
                    out.write("<p>");
                }
                for (int i = header + 1; i < sb.length(); i++) {
                    char c = sb.charAt(i);
                    if (c == '\\') {
                        continue;
                    }
                    if (remake[i] != bad) {
                        byte pos = remake[i];
                        out.write(remade[pos]);
                        if (mark[pos%mark.length].length() == 2) {
                            i++;
                        }
                    } else {
                        if (c == '<' || c == '>' || c == '&') {
                            out.write(special[find(special, String.valueOf(c)) + 3]);
                        } else {
                            out.write(c);
                        }
                    }
                }
                if (header != -1) {
                    out.write("</h" + header + ">");
                } else {
                    out.write("</p>");
                }
                out.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error while opening files: " + e.getMessage());
        }
    }

    private static byte find(String[] mark, String s) {
        for (int i = 0; i < mark.length; i++) {
            if (mark[i].equals(s)) {
                return (byte) i;
            }
        }
        return (byte) 0xff;
    }
}
