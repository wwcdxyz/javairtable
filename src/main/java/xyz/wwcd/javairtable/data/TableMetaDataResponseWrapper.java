package xyz.wwcd.javairtable.data;

import java.util.List;

public record TableMetaDataResponseWrapper(
        List<TableMetaData> tables
) {

}
