package info.androidhive.materialtabs.activity;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class editmemberRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://140.127.22.42:82/EditMember.php";
    private Map<String, String> params;

    public editmemberRequest(String name, String email, String password,String nickname, String cellphone, String birthday,String sex, String address,  Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("nickname", nickname);
        params.put("cellphone", cellphone);
        params.put("birthday", birthday);
        params.put("sex", sex);
        params.put("address", address);

    }



    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
