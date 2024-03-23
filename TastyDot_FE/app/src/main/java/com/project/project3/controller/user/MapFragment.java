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

import com.kakao.util.maps.helper.Utility;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;
import com.project.project3.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MapFragment extends Fragment implements MapView.POIItemEventListener, MapView.MapViewEventListener{
    private MapPOIItem marker;
    private CardView cardView;
    private TextView tvCardStore;
    private TextView tvCardAdress;
    private TextView tvCardScore;
    private TextView tvCardReviews;
    private ImageView imgCardimg1;
    private ImageView imgCardimg2;
    private ImageView imgCardimg3;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        MapView mapView = view.findViewById(R.id.mv);

        // 맵뷰 시작 위도,경도
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(34.808258, 126.390906), true);

        // MARKER_POINT를 위도,경도 설정하면 마커가 찍힌다.
        // setItemName은 음식점 이름
        MapPoint MARKER_POINT1 = MapPoint.mapPointWithGeoCoord(34.808258, 126.390906);
        MapPOIItem marker1 = new MapPOIItem();
        marker1.setItemName("은선식당");
        marker1.setTag(0);
        marker1.setMapPoint(MARKER_POINT1);
        marker1.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker1.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        MapPoint MARKER_POINT2 = MapPoint.mapPointWithGeoCoord(34.80668878897797, 126.39139390797234);
        MapPOIItem marker2 = new MapPOIItem();
        marker2.setItemName("세명식당");
        marker2.setTag(1);
        marker2.setMapPoint(MARKER_POINT2);
        marker2.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는

        mapView.addPOIItem(marker1);
        mapView.addPOIItem(marker2);

        //카카오맵, 마커 이벤트 연결
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);

        cardView = view.findViewById(R.id.cardvDetail);
        tvCardStore = view.findViewById(R.id.tvCardStore);
        tvCardAdress = view.findViewById(R.id.tvCardAdress);
        tvCardScore = view.findViewById(R.id.tvCardScore);
        tvCardReviews = view.findViewById(R.id.tvCardReviews);
        imgCardimg1 = view.findViewById(R.id.imgCardimg1);
        imgCardimg2 = view.findViewById(R.id.imgCardimg2);
        imgCardimg3 = view.findViewById(R.id.imgCardimg3);

        // 카드뷰 초기에 숨기기
        cardView.setVisibility(View.GONE);

        return view;
    }

    // POI(마커) 관련 이벤트
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        cardView.setVisibility(View.VISIBLE);
        tvCardStore.setText(mapPOIItem.getItemName());
        tvCardAdress.setText("주소 정보");
        tvCardScore.setText("평점 정보");
        tvCardReviews.setText("리뷰 정보");
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

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        if (mapView.getZoomLevel()>=3){
            mapView.removeAllPOIItems();
        }else {
            MapPoint MARKER_POINT1 = MapPoint.mapPointWithGeoCoord(34.808258, 126.390906);
            MapPOIItem marker1 = new MapPOIItem();
            marker1.setItemName("은선식당");
            marker1.setTag(0);
            marker1.setMapPoint(MARKER_POINT1);
            marker1.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker1.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

            MapPoint MARKER_POINT2 = MapPoint.mapPointWithGeoCoord(34.80668878897797, 126.39139390797234);
            MapPOIItem marker2 = new MapPOIItem();
            marker2.setItemName("세명식당");
            marker2.setTag(1);
            marker2.setMapPoint(MARKER_POINT2);
            marker2.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는

            mapView.addPOIItem(marker1);
            mapView.addPOIItem(marker2);




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