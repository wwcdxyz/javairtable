package xyz.wwcd.javairtable.data;

import java.util.Date;
import java.util.Map;

public record AirtableRecordData(
        String id,
        Date createdTime,
        Map<String, Object> fields
) {
}
