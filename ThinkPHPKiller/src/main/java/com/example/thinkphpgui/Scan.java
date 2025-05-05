package com.example.thinkphpgui;

import exploit.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class Scan {
    private final ConfigManager configManager = ConfigManager.getInstance();
    private final ExecutorService executorService;
    private final Map<String, Exploit> exploitMap = new HashMap<String, Exploit>() {{
        put("Tp2 RCE", new Tp2_Rce());
        put("Tp3 Log RCE", new Tp3_Log_Rce());
        put("Tp5 数据库信息泄露", new Tp5_DB_Leak());
        put("Tp5 文件包含", new Tp5_Lfi());
        put("Tp CVE-2022-25481", new Tp_CVE_2022_25481());
        put("Tp5 sql注入", new Tp5_Sql());
        put("Tp6 Lang 文件包含 RCE", new Tp6_Lang_Lfi_Rce());
        put("Tp5 PHPSESSID 文件包含 RCE", new Tp5_PHPSESSID_Lfi_Rce());
        put("Tp CVE-2019-9082", new Tp_CVE_2019_9082());
        put("Tp CVE-2018-20062", new Tp_CVE_2018_20062());
        put("Tp 日志泄露", new Tp_Log_Leak());
    }};

    public Scan() {
        int threads = Integer.parseInt(configManager.threadsTextField.getText());
        executorService = Executors.newFixedThreadPool(threads);
        initialize();
    }

    private void initialize() {
        ConfigManager.disableSSL();
        if (configManager.enableProxy != null && configManager.enableProxy) {
            configManager.setHttpProxy(configManager.proxyIp, configManager.proxyPort);

        } else {
            configManager.clearProxy();
        }
        configManager.urlList.clear();
        if (configManager.scanType.equals("byUrl")) {
            if (!configManager.urlTextField.getText().endsWith("/")) {
                configManager.urlList.add(configManager.urlTextField.getText() + "/");
            } else {
                configManager.urlList.add(configManager.urlTextField.getText());
            }

        }
        if (configManager.scanType.equals("byFile")) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(configManager.urlFileTextField.getText()));
                configManager.urlList.clear();
                String url;
                while ((url = bufferedReader.readLine()) != null) {
                    if (!url.isEmpty() && !url.endsWith("/")) {
                        url = url + "/";
                        configManager.urlList.add(url);
                    } else {
                        configManager.urlList.add(url);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void startScan() throws IOException {
        List<Exploit> exploitList = getExploitList();
        for (String url : configManager.urlList) {
            for (Exploit exploit : exploitList) {
                executorService.submit(() -> {
                    try {
                        exploit.startScan(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        executorService.shutdown();

    }

    private List<Exploit> getExploitList() {
        String selected = configManager.vulnComboBox.getValue();
        List<Exploit> selectedExploits = new ArrayList<>();
        if (selected == null || selected.trim().isEmpty()) {
            return selectedExploits;
        }
        selected = selected.trim();

        if ("ALL".equalsIgnoreCase(selected)) {
            selectedExploits.addAll(exploitMap.values());
        } else {
            Exploit exploit = exploitMap.get(selected);
            if (exploit != null) {
                selectedExploits.add(exploit);
            }
        }
        return selectedExploits;
    }

}