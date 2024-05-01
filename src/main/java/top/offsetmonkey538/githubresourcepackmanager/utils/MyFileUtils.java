package top.offsetmonkey538.githubresourcepackmanager.utils;

import top.offsetmonkey538.githubresourcepackmanager.GithubResourcepackManager;
import top.offsetmonkey538.githubresourcepackmanager.exception.GithubResourcepackManagerException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public final class MyFileUtils {
    private MyFileUtils() {

    }

    public static File createDir(File file) throws GithubResourcepackManagerException {
        if (!file.exists() && !file.mkdirs())
            throw new GithubResourcepackManagerException("Failed to create directory '%s'!", file);
        return file;
    }

    public static void createNewFile(File file) throws GithubResourcepackManagerException {
        if (file.exists() && !file.delete())
            throw new GithubResourcepackManagerException("Failed to delete file '%s'!", file);

        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        } catch (IOException e) {
            throw new GithubResourcepackManagerException("Failed to create file '%s'!", e, file);
        }
    }

    public static boolean downloadFile(String url, File output, String... auth) {

        boolean success = true;
        InputStream in = null;
        FileOutputStream out = null;

        try {

            URL myUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setDoOutput(true);
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestMethod("GET");

            if (auth != null && auth.length >= 2) {
                String userCredentials = auth[0].trim() + ":" + auth[1].trim();
                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
                conn.setRequestProperty("Authorization", basicAuth);
            }
            in = conn.getInputStream();
            out = new FileOutputStream(output.getName());
            int c;
            byte[] b = new byte[1024];
            while ((c = in.read(b)) != -1) out.write(b, 0, c);

        } catch (Exception ex) {
            GithubResourcepackManager.LOGGER.error(("There was an error downloading " + output.getName() + ". Check console for details."));
            ex.printStackTrace();
            success = false;
        } finally {
            if (in != null) try {
                in.close();
            } catch (IOException e) {
                GithubResourcepackManager.LOGGER.error(("There was an error downloading " + output.getName() + ". Check console for details."));
                e.printStackTrace();
            }
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                GithubResourcepackManager.LOGGER.error(("There was an error downloading " + output.getName() + ". Check console for details."));
                e.printStackTrace();
            }
        }
        return success;
    }
}
