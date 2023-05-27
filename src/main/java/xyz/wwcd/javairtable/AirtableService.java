package xyz.wwcd.javairtable;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import xyz.wwcd.javairtable.data.AirtableRecordData;
import xyz.wwcd.javairtable.data.AirtableResponseWrapper;
import xyz.wwcd.javairtable.data.TableMetaData;
import xyz.wwcd.javairtable.data.TableMetaDataResponseWrapper;
import xyz.wwcd.javairtable.exception.AirtableException;

import java.util.Collections;
import java.util.List;

public class AirtableService {

    public static final String AIRTABLE_API_URL_PREFIX = "https://api.airtable.com/v0/";

    private static final String AIRTABLE_META_API_URL_PREFIX = "meta/bases/";

    private static final String AIRTABLE_META_API_URL_SUFFIX = "/tables";
    private final HttpHeaders headers;

    public AirtableService(String personalAuthenticationToken) {
        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(personalAuthenticationToken);
    }

    public static String composeAirtableApiUrl(String baseId, String tableName) {
        return AIRTABLE_API_URL_PREFIX + baseId + "/" + tableName;
    }

    public static String composeMetaApiUrl(String baseId) {
        return AIRTABLE_API_URL_PREFIX + AIRTABLE_META_API_URL_PREFIX + baseId + AIRTABLE_META_API_URL_SUFFIX;
    }

    public List<AirtableRecordData> readRecords(String baseId, String tableName) throws AirtableException {
        HttpEntity<AirtableResponseWrapper> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AirtableResponseWrapper> responseEntity = restTemplate.exchange(composeAirtableApiUrl(baseId, tableName), HttpMethod.GET, request,
                AirtableResponseWrapper.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return responseEntity.getBody() != null ? responseEntity.getBody().records() : Collections.emptyList();
        } else {
            throw new AirtableException("Error reading records from Airtable, status code: "
                    + responseEntity.getStatusCode());
        }
    }

    public List<TableMetaData> getTablesList(String baseId) throws AirtableException {
        HttpEntity<TableMetaData> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TableMetaDataResponseWrapper> responseEntity = restTemplate.exchange(composeMetaApiUrl(baseId), HttpMethod.GET, request,
                TableMetaDataResponseWrapper.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return responseEntity.getBody() != null ? responseEntity.getBody().tables() : Collections.emptyList();
        } else {
            throw new AirtableException("Error reading records from Airtable, status code: "
                    + responseEntity.getStatusCode());
        }
    }

}
