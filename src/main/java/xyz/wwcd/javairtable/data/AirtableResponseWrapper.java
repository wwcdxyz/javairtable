package xyz.wwcd.javairtable.data;

import java.util.List;

public record AirtableResponseWrapper(
        List<AirtableRecordData> records
) {

}
