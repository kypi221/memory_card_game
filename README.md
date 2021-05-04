# memory card game

Game Trúc xanh

## Gồm các tính năng

- setting số ô
- setting thời gian
- Màn hình win/lose
- Update logo và background color của đối tác ( yêu cầu từ phía Cty Freelance )

## Built With

* [Dagger2, RX, Clean Architecture](https://github.com/android10/Android-CleanArchitecture) - Cấu trúc
* [ButterKnife](http://jakewharton.github.io/butterknife/) - Thư viện bind view
* [Picasso](http://square.github.io/picasso/) - Thư viện load hình
* [EasyFlipView](https://github.com/wajahatkarim3/EasyFlipView/) - Thư viện dùng để tạo hiệu ứng lật
* [LeonidsLib](https://github.com/plattysoft/Leonids/) - Thư viện dùng để tạo hiệu ứng pháo hoa ( khi win game )


### Nhược điểm

- hard code layout ( dành cho 1 Android Tivi với resolution 1920 x 1080 ) nên đa số các code layout đều bị hard code, bạn muốn update thì chỉ cần vào edit lại các chỉ số của layout )

```
 <Button
            android:id="@+id/btn_bg_color"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_margin="20px"
            android:layout_weight="1"
            android:padding="40px"
            android:text="Đổi màu nền"
            android:textSize="40px" />
```

