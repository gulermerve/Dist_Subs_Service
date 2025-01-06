# Dağıtık Abonelik Sistemi (Distributed Subscriber Service)

Bu projede, Ruby, Python ve Java kullanılarak HASUP (Hata-Tolere Abonelik Servisi Üyelik Protokolü) üzerine bir abonelik sistemi geliştirdik.Ve aşağıdaki şekilde adım adım uyguladık.

Merve:
- Protobuf tanımlamalarını yaptık:
  Subscriber mesajı abone bilgilerini içerir.
  Capacity mesajı sunucu kapasitesini sorgular.
  Configuration mesajı, sunucunun yapılandırmasını günceller.
- Komutlar gönderen ve sunuculardan cevap alan bir istemci uygulamasını java dilinde yazdık.Bu kod, sunucularla bağlantı kurar, kullanıcının komutlarını işler ve ilgili yanıtları alır.Yanıt, bir String olarak okunup kullanıcıya gösterilir.

Leila:
- Server1.java, Server2.java ve Server3.java dosyaları birbirleriyle TCP protokolü kullanarak bağlantı kurulmuştur.Server1 de sunucu, bir hata durumunda çalışmaya devam etmez ve kapanır.Server2 de sunucu, 2 hata yapmasına izin verir. Yani iki hata durumunda sunucu çalışmaya devam eder, fakat üçüncü hatada kapanır.Server3 sunucusu ise bağlantı hatalarında yalnızca 1 hata toleransı vardır ve bu hatada sunucu kapanır. Protobuf hatalarında ise 2 hata toleransı vardır ve üçüncü hatada kapanır.

Melek:
- Admin.rb dosyasında Ruby'nin socket kütüphanesi kullanılarak sunucularla TCP bağlantısı kurulur.
Gönderilen mesaj ve alınan yanıt Ruby string formatında işlenir.
Sunuculardan gelen yanıt ekrana yazdırılır. Yanıtların içinde hata varsa o sunucunun hata sayısı bir arttırılır, hata sayısını ekrana yazdırır.
Eğer hata sayısı hata toleransını geçerse sunucunun kapatılması gerektiği belirtilir.

- Plotter.py dosyasında sunucuların hata sayısı matplotlib kullanılarak bir çubuk grafik şeklinde görselleştirilir. Her sunucunun farklı bir rengi vardır ve zamanla bu grafik güncellenir.

- Sonuç olarak;
Sunucular arası iletişim kurduk.
İstemci ve admin kontrol mekanizmalarını tamamladık.
Hata sayılarını plotter'a gönderdik ve grafikle görselleştirdik.


### plotter.py Solar System Exploration, 1950s – 1960s

- [x] Sunuculara mesaj gönderilir.
- [x] Her sunucu için bir hata sayacı tanımlanır:
- [x] Sunucuların yanıtları ekrana yazdırılır
- [x] Her bir sunucu için hata sayıları yazdırılır
- [x] Matplotlib kullanılarak hata sayıları bir çubuk grafikle görselleştirilir:
- [ ] Saturn
- [ ] Uranus
- [ ] Neptune
- [ ] Comet Haley

### admin.rb Solar System Exploration, 1950s – 1960s

- [x] TCP istemci uygulaması olarak tasarlanmıştır.
- [x] Aynı makine üzerinde çalışan sunuculara bağlanmayı hedefler.
- [x] Her sunucuya bir mesaj gönderir
- [x] Her bir sunucu için bir hata sayacı tanımlanmıştır.
- [ ] Jupiter
- [ ] Saturn
- [ ] Uranus
- [ ] Neptune
- [ ] Comet Haley

### ServerX.java Solar System Exploration, 1950s – 1960s

- [x] admin_client.rb ile başlama
- [x] server1 hata toleransı 1 prensibiyle çalışma
- [x] server2 hata toleransı 2 prensibiyle çalışma
- [x] server3 hata toleransı 2 prensibiyle çalışma
- [x] Sunucular aynı makinede çalışacak şekilde tasarlanmıştır.
- [ ] Jupiter
- [ ] Saturn
- [ ] Uranus
- [ ] Neptune



Ekip üyeleri:
- 22060342, Melek KÖŞELİ
- 22060401, Emine Merve GÜLER
- 22060781, Leila SARAFARAZ


Sunum Videosu Linki:
https://drive.google.com/file/d/168RAd4_sd0Ik921lynAcCj3_w-NlKAKe/view?usp=sharing