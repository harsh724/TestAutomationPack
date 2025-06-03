package payloads;

public class JSONPayload {
    public String firstPayload(String key1, String key2){
        return "" +
                "{\n"+
                    "   \"title\": \"" +key1+"\",\n"+
                    "   \"body\": \"This is a sample "+key2+ " request\",\n"+
                    "   \"userId\": 101"+"\n"+
                "}";
    }
}
