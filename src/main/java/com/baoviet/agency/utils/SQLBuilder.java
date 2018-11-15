package com.baoviet.agency.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.log4j.Logger;

import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author Nam, Nguyen Hoai
 *
 */
public class SQLBuilder {

    public static final Logger LOG = Logger.getLogger(SQLBuilder.class);
    public final static String SQL_MODULE_GNOC_CR = "gnoc_cr";

    public final static String SQL_MODULE_COMMON = "common";

    public final static String SQL_MODULE_GDTT = "gdtt";
    public final static String SQL_MODULE_GDTT_CDBR = "gdtt_cdbr";
    public final static String SQL_MODULE_CDBR = "cdbr";
    public final static String SQL_MODULE_CDBR_TKM = "cdbr/tkm";
    public final static String SQL_MODULE_CDBR_REPORT = "cdbr/report";

    public final static String SQL_MODULE_CDBR_PAKH = "cdbr_pakh";

    public final static String SQL_MODULE_SCTD = "sctd";
    public final static String SQL_MODULE_WO = "wo";
    public final static String SQL_MODULE_PACLM = "paclm";
    public final static String SQL_MODULE_KTHT = "ktht";
    public final static String SQL_MODULE_MANAGE_REDUCE = "qlgt";
    
    public final static String SQL_MODULE_VT_NET_REPORT = "vtnet_report";
    
    public final static String SQL_MODULE_DASHBOARD = "dashboard";
    
    public final static String SQL_MODULE_QLTTDB = "qlttdb";
    
    public final static String SQL_MODULE_MANAGE_TARGET = "target";

    public static String getSqlQueryById(String module,
            String queryId) {
        File folder = null;
        try {
            folder = new ClassPathResource(
                    "sql" + File.separator + module + File.separator + queryId + ".sql").getFile();

            // Read file
            if (folder.isFile()) {
                String sql = new String(Files.readAllBytes(Paths.get(folder.getAbsolutePath())));
                return sql;
            }
        } catch (IOException e) {
            LOG.error(e);
            return null;
        }
        return null;
    }

}
