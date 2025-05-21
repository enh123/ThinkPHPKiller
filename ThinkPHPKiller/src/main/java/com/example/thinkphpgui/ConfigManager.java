package com.example.thinkphpgui;

import javafx.application.Platform;
import javafx.scene.control.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.soap.Text;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;

public class ConfigManager {
    private static ConfigManager configManager;
    public TextField urlTextField, urlFileTextField, threadsTextField;
    public ComboBox<String> vulnComboBox = new ComboBox<>();
    public Button importUrlButton, startScanButton, clearButton, getShellButton;
    public TextArea infoTextArea;
    public Boolean enableProxy;
    public static Boolean enableHeaders;
    public String scanType;
    public String proxyIp;
    public String proxyPort;
    public String headers;
    public Boolean isRunning;

    public static final List<String> moduleList = Arrays.asList(
            "0",
            "%20",
            "%5c",
            "about",
            "addlog",
            "admin",
            "api",
            "calculation",
            "captcha",
            "circle",
            "home",
            "index",
            "notify",
            "www",
            "wxnotify"
            "chat",
            "common",
            "company",
            "data",
            "download",
            "extra",
            "front",
            "sso",
            "upload"
            
    );
    public List<String> urlList=new ArrayList<>();

    public static Map<String, String> headersMap = new HashMap<>();

    public Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void initialize(TextField urlTextField, TextField urlFileTextField, TextField threadsTextField, ComboBox<String> vulnComboBox, Button importUrlButton, Button startScanButton, Button clearButton, Button getShellButton, TextArea infoTextArea) {
        this.urlTextField = urlTextField;
        this.urlFileTextField = urlFileTextField;
        this.threadsTextField = threadsTextField;
        this.vulnComboBox = vulnComboBox;
        this.importUrlButton = importUrlButton;
        this.startScanButton = startScanButton;
        this.clearButton = clearButton;
        this.getShellButton = getShellButton;
        this.infoTextArea = infoTextArea;

    }

    //单例模式
    public static synchronized ConfigManager getInstance() {
        if (configManager == null) {
            configManager = new ConfigManager();
            return configManager;
        } else {
            return configManager;
        }
    }


    public void setAlert(String headerText, String contentText) {
        this.alert.setHeaderText(headerText);
        this.alert.setContentText(contentText);
        this.alert.showAndWait();
    }

    public void appendToTextArea(String text) {
        Platform.runLater(() -> {
            this.infoTextArea.appendText(text + "\n");
        });
    }


    public static void disableSSL() {
        try {
            TrustManager[] trustAll = new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAll, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHttpProxy(String proxyHost, String proxyPort) {
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);
    }


    public void clearProxy() {
        System.clearProperty("http.proxyHost");
        System.clearProperty("http.proxyPort");
        System.clearProperty("https.proxyHost");
        System.clearProperty("https.proxyPort");
    }


}
