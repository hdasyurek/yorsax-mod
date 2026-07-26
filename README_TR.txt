===============================================
MINECRAFT DUNYA SIFRE MODU - KURULUM REHBERI
===============================================

1. GEREKSINIMLER
----------------
- Java JDK 21+ yuklu olmali
- Gradle yuklu olmali (wrapper kullanacaksan gerekmez)

2. DERLEME ADIMLARI
-------------------
A) Bu klasoru bir yere cikart (orn: Masaustu/password-mod)

B) Komut satirini ac (CMD, PowerShell veya Terminal)
   Klasor icine gir:
   cd Masaustu/password-mod

C) Gradle wrapper olustur (eger gradlew yoksa):
   gradle wrapper --gradle-version 8.8

D) Modu derle:
   Windows: gradlew.bat build
   Mac/Linux: ./gradlew build

E) Bitti! Jar dosyasi su konumda olusacak:
   build/libs/password-mod-1.0.0.jar

3. MINECRAFTA KURULUM
---------------------
- Fabric Loader 26.2 icin yuklu olmali
- Fabric API 26.2 modu mods klasorunde olmali
- build/libs/ icindeki password-mod-1.0.0.jar dosyasini
  .minecraft/mods/ klasorune at

4. MOD NASIL CALISIR?
---------------------
- Herhangi bir dunyaya ilk girisinde sifre olusturman istenir
- Sifre olusturunca sana 8 haneli YEDEK KOD verilir
  (Bu kodu bir yere yaz, sifreni unutursan bununla sifirlayacaksin)
- Sonraki girislerinde sifre sorulur
- Sifre yanlissa oyuna giremezsin
- "Sifremi Unuttum" ile yedek kodu girerek sifreyi kaldirabilirsin

5. SIFRELER NEREDE SAKLANIR?
-----------------------------
- .minecraft/config/passwordmod/passwords.json
- Bu dosyayi silersen tum sifreler silinir

6. NOTLAR
---------
- 26.2 icin yarn_mappings ve fabric_version degerlerini
  Fabric wiki'den kontrol edip gradle.properties'de guncelle
- Eger derleme hatasi alirsan, muhtemelen mapping isimleri
  farklidir. IDE'de (IntelliJ) hatali satirlari duzelt.

Kolay gelsin!
===============================================