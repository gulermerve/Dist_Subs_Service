# Dağıtık Abonelik Sistemi (Distributed Subscriber Service)
Bu projede, Ruby, Python ve Java kullanılarak HASUP (Hata-Tolere Abonelik Servisi Üyelik Protokolü) üzerine bir abonelik sistemi geliştirdik.Ve aşağıdaki şekilde adım adım uyguladık.

Leila:
- Server1.java, Server2.java ve Server3.java dosyaları birbirleriyle TCP protokolü kullanarak bağlantı kurulmuştur. 

Melek:
- Admin.rb ile sunucular başlatılarak sunucuların "STRT" komutunu işleyip yanıt vermesi sağlandı.Yani:
Admin, her sunucuya STRT komutunu gönderecek.Sunucuların yanıtları:
Komutun başarılı işlenmesi durumunda Message nesnesi ile YEP yanıtı döner.
Komutun başarısız işlenmesi durumunda NOP yanıtı döner.

Merve:
- Admin, STRT komutunu tüm sunuculara gönderir. YEP yanıtı dönen sunucular listeye eklenir. Admin, her 5 saniyede bir listeye eklenen sunuculara CPCTY komutunu gönderir.
Sunucular, mevcut kapasite ve zaman bilgisini Capacity nesnesi olarak geri gönderir. Ayrıca kapasite verisini plotter.py sunucusuna da gönderecek.

Leila - Merve:
- Gelen kapasite verilerini alacak ve bu verileri bir grafik üzerinde gösterecek bir plotter.py kodunu yazdık. plotter.py gelen veriyi alır, her sunucuya ait kapasiteyi toplar ve anlık olarak bir çizgi grafik üzerinde gösterir. Her sunucunun farklı bir rengi vardır ve zamanla bu grafik güncellenir.

Melek - Leila:
- Abone bilgilerini saklayacak ve gerekli işlemleri yapacak Subscriber sınıfını tanımladık.Sunucular, SUBS komutu ile yeni bir abone ekleniyor. Abone, Subscriber nesnesi olarak sunucuda bir Map içinde saklanıyor. DEL komutu ile belirtilen ID'ye sahip abone siliniyor. Eğer abone bulunamazsa, hata mesajı gönderiliyor.
Fault_tolerance_level = 1 olarak belirlendiğinde sistem sorunsuz bir şekilde çalışmıştır.
Bir sunucu devre dışı kaldığında bile sistem diğer iki sunucuyla çalışmaya devam etmiştir.

Melek - Merve:
- Her sunucu, plotter.py sunucusuna bağlanıyor. Bu bağlantı üzerinden kapasite bilgileri ve diğer verileri gönderilir.

Melek - Merve - Leila:
- Komutlar gönderen ve sunuculardan cevap alan bir istemci uygulamasını java dilinde yazdık.Bu kod, sunuculara bağlantı kurar, kullanıcının komutlarını işler ve ilgili yanıtları alır.

- Sonuç olarak;
Sunucular arası iletişim kurduk.
İstemci ve admin kontrol mekanizmalarını tamamladık.
Kapasite verileri düzenli olarak plotter'a gönderdik ve grafikle görselleştirdik.

- 

### plotter.py Solar System Exploration, 1950s – 1960s

- [ ] Mercury
- [x] Venus
- [x] Earth (Orbit/Moon)
- [x] Mars
- [ ] Jupiter
- [ ] Saturn
- [ ] Uranus
- [ ] Neptune
- [ ] Comet Haley

### admin.rb Solar System Exploration, 1950s – 1960s

- [ ] Mercury
- [x] Venus
- [x] Earth (Orbit/Moon)
- [x] Mars
- [ ] Jupiter
- [ ] Saturn
- [ ] Uranus
- [ ] Neptune
- [ ] Comet Haley

### ServerX.java Solar System Exploration, 1950s – 1960s

- [ ] Mercury
- [x] Venus
- [x] Earth (Orbit/Moon)
- [x] Mars
- [ ] Jupiter
- [ ] Saturn
- [ ] Uranus
- [ ] Neptune
- [ ] Comet Haley
