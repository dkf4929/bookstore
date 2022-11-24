package project.bookstore.api;

import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import project.bookstore.dto.api.ApiResultDto;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ApiSearch {

    public List<ApiResultDto> callApi(String url, String title) {
        String clientId = "IDaPCa1nqmtOq8YWlHhW"; // api_key
        String clientSecret = "hdbOLCAevR"; // api_secret
        String text = null;
        List<ApiResultDto> result = new ArrayList<>();

        try {
            text = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }


        String apiURL = url + text;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(responseBody);
            JSONObject jsonMain = (JSONObject)obj;
            JSONArray jsonArr = (JSONArray)jsonMain.get("items");

            if (jsonArr.size() > 0){
                for(int i=0; i<jsonArr.size(); i++){
                    JSONObject jsonObj = (JSONObject)jsonArr.get(i);

                    ApiResultDto dto = null;

                    if (url.contains("book")) {
                        dto = ApiResultDto.builder()
                            .content1((String) jsonObj.get("title"))
                            .content2((String) jsonObj.get("author"))
                            .content3((String) jsonObj.get("isbn"))
                            .content4(10000)
                            .build();
                    } else {
                        dto = ApiResultDto.builder()
                                .content1((String) jsonObj.get("roadAddress"))
                                .content2((String) jsonObj.get("address"))
                                .content3((String) jsonObj.get("title"))
                                .build();
                    }

                    result.add(dto);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
