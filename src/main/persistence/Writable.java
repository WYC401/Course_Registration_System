package persistence;

import org.json.JSONObject;

import java.io.IOException;

// the interface represents the classes which can turn itself to json

public interface Writable {
    // EFFECT: return this as JSON object
    JSONObject toJson() throws IOException;
}
