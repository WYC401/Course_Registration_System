package persistence;

import org.json.JSONObject;

import java.io.IOException;

public interface Writable {
    JSONObject toJson() throws IOException;
}
