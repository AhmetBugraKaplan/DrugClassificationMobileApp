# Drug Classification Android App

Bu proje, makine öğrenmesi kullanarak ilaç sınıflandırması yapan bir Android uygulamasıdır. Uygulama, hasta bilgilerini alarak hangi ilacın uygun olacağını tahmin eder.

## Özellikler

- **Modern Android Geliştirme**: Jetpack Compose ile modern UI
- **Clean Architecture**: Domain, Data ve Presentation katmanları
- **Dependency Injection**: Hilt ile bağımlılık yönetimi
- **Network Communication**: Retrofit ile API iletişimi
- **Material Design 3**: Modern ve kullanıcı dostu arayüz

## Teknolojiler

- **Kotlin** - Programlama dili
- **Jetpack Compose** - UI framework
- **Hilt** - Dependency injection
- **Retrofit** - Network client
- **Moshi** - JSON parsing
- **Coroutines** - Asenkron programlama
- **Material Design 3** - UI tasarım sistemi

## Proje Yapısı

```
app/src/main/java/com/example/drugclassificationcursor/
├── data/
│   ├── mapper/          # Data-Domain mapping
│   ├── remote/          # API interfaces ve DTOs
│   └── repository/      # Repository implementations
├── di/                  # Dependency injection modules
├── domain/
│   ├── model/           # Domain models
│   ├── repository/      # Repository interfaces
│   └── usecase/         # Business logic
├── presentation/
│   ├── DrugScreen.kt    # Ana ekran
│   ├── DrugViewModel.kt # ViewModel
│   └── DrugUiState.kt   # UI state
└── ui/theme/            # UI tema dosyaları
```

## Kurulum

1. Projeyi klonlayın:
```bash
git clone <repository-url>
cd DrugClassificationCursor
```

2. Android Studio'da projeyi açın

3. Gradle sync işlemini tamamlayın

4. Uygulamayı çalıştırın

## API Gereksinimleri

Uygulama, `http://10.0.2.2:5000/predict` endpoint'ine POST isteği gönderir. Bu endpoint'in aşağıdaki formatta veri alması gerekir:

```json
{
  "Age": 25,
  "Na_to_K": 12.5,
  "Sex_M": true,
  "BP_LOW": false,
  "BP_NORMAL": true,
  "Cholesterol_NORMAL": false
}
```

Ve aşağıdaki formatta yanıt döner:

```json
{
  "prediction": "DrugY"
}
```

## Kullanım

1. Uygulamayı açın
2. Hasta bilgilerini girin:
   - **Age**: Yaş (sayısal)
   - **Na_to_K**: Na/K oranı (ondalıklı sayı)
   - **Sex_M**: Cinsiyet (erkek için açık)
   - **BP**: Kan basıncı seviyesi (HIGH/LOW/NORMAL)
   - **Cholesterol**: Kolesterol seviyesi (HIGH/NORMAL)
3. "Tahmin Et" butonuna tıklayın
4. Sonuç ekranda görüntülenecektir

## Geliştirme

### Clean Architecture Prensipleri

- **Domain Layer**: İş mantığı ve domain modelleri
- **Data Layer**: Veri kaynakları ve repository implementasyonları
- **Presentation Layer**: UI ve ViewModel'ler

### Dependency Injection

Hilt kullanılarak bağımlılıklar yönetilir. `@HiltAndroidApp` annotation'ı Application sınıfında, `@AndroidEntryPoint` MainActivity'de kullanılır.

### State Management

ViewModel'de `StateFlow` kullanılarak UI state'i yönetilir. Compose'da `collectAsState()` ile state'e abone olunur.

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır.
