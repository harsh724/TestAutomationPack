package payloads;

public class JSONPayload {
    public String firstPayload(String param1, String param2){
        return "{" +
                " \"field1\": \"" + param1+"\",\n"+
                 "\"field2\": \"" + param2+"\",\n"+
                "}";
    }
}
