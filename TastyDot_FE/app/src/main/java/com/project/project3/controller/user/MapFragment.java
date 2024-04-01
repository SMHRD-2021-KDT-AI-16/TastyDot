package com.project.project3.controller.user;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.util.maps.helper.Utility;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import com.project.project3.R;
import com.project.project3.adapterViewholder.CouponAdapter;
import com.project.project3.model.UserCouponVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.Inflater;

public class MapFragment extends Fragment implements MapView.POIItemEventListener, MapView.MapViewEventListener {
    private MapPOIItem marker;
    private CardView cardView;
    private TextView tvCardStore;
    private TextView tvCardAdress;
    private TextView tvCardScore;
    private TextView tvCardReviews;
    private ImageView imgCardimg1;
    private ImageView imgCardimg2;
    private ImageView imgCardimg3;
    private String hashtag;
    private String addr;
    String name;

    private RequestQueue requestQueue;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<MapPOIItem> allMarkers = new ArrayList<>();


    public MapFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    MapView mapView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getContext());
        }

        view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = view.findViewById(R.id.mv);

        // 맵뷰 시작 위도,경도
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(34.808258, 126.390906), true);

        getStore();

        //카카오맵, 마커 이벤트 연결
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);

        cardView = view.findViewById(R.id.cardvDetail);
        tvCardStore = view.findViewById(R.id.tvCardStore);
        tvCardAdress = view.findViewById(R.id.tvCardAdress);
        tvCardScore = view.findViewById(R.id.tvCardScore);
        imgCardimg1 = view.findViewById(R.id.imgCardimg1);
        imgCardimg2 = view.findViewById(R.id.imgCardimg2);
        imgCardimg3 = view.findViewById(R.id.imgCardimg3);

        // 카드뷰 초기에 숨기기
        cardView.setVisibility(View.GONE);

        return view;
    }

    private void getStore() {
        String url = "http://192.168.219.101:8081/api/getStoreInfo"; // 가게 정보 조회 API 엔드포인트, 실제 엔드포인트로 수정 필요
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // 응답으로 받은 JSON 배열을 순회하면서 가게 정보 처리
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject storeInfo = response.getJSONObject(i);
                            String storeName = storeInfo.getString("storeName"); // 가게 이름
                            hashtag = storeInfo.getString("hashtag");
                            addr = storeInfo.getString("addr");
                            double latitude = 0.0;
                            double longitude = 0.0;
                            try {
                                latitude = Double.parseDouble(storeInfo.getString("latitude")); // 위도
                                longitude = Double.parseDouble(storeInfo.getString("longitude")); // 경도
                            } catch (NumberFormatException e) {
                                Log.e("getStore", "Latitude or Longitude parse error", e);
                            }


                            // 마커 생성 및 지도에 추가
                            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
                            MapPOIItem marker = new MapPOIItem();
                            marker.setItemName(storeName);
                            marker.setTag(i); // 고유 식별자로 사용할 수 있는 태그 설정
                            marker.setMapPoint(mapPoint);
                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 마커 모양
                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 선택된 마커 모양
                            mapView.addPOIItem(marker); // mapView는 지도 객체의 참조, 클래스 멤버 변수로 관리되어야 함
                            allMarkers.add(marker);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("실패", "가게정보 불러오는데 실패");
//                        Toast.makeText(getContext(), "가게 정보를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
//                    Toast.makeText(getContext(), "가게 정보를 불러오는 데 실패했습니다: " + error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("에러", "가게정보 불러오는데 에러" + error.toString());
                });

        requestQueue.add(jsonArrayRequest); // requestQueue는 Volley의 RequestQueue 객체의 참조, 클래스 멤버 변수로 관리되어야 함
    }
    // POI(마커) 관련 이벤트

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        cardView.setVisibility(View.VISIBLE);
        tvCardStore.setText(mapPOIItem.getItemName());
        name = mapPOIItem.getItemName();
        // 여기서 api 하나 더 만들기

        getHashtag();
        tvCardAdress.setText(addr);
        tvCardScore.setText(hashtag);
    }

    public void getHashtag() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getContext());
        }
        String url = "http://192.168.219.101:8081/api/getHashtag";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            hashtag = jsonResponse.getString("hashtag");
                            addr = jsonResponse.getString("addr");


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // 아이디가 없어 response객체가 null 값일 떄
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.getMessage());
                    }
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("storeName", name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonBody.toString().getBytes();
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                if (headers == null || headers.isEmpty()) {
                    headers = new HashMap<>();
                }
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
    //MapView 관련 이벤트

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    boolean mark = true;

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        if (mapView.getZoomLevel() >= 3) {
            mark = false;
            mapView.removeAllPOIItems();
        } else {
            if (!mark) {
                for (MapPOIItem marker : allMarkers) {
                    mapView.addPOIItem(marker);
                }
                mark = true;
            }

        }
    }


    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        cardView.setVisibility(View.GONE);
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
    }


}