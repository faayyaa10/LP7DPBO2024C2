# LP7DPBO2024C2

/* Saya Talitha Fayarina Adhigunawan [2201271] mengerjakan LP7 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin. */

Program ini adalah implementasi sederhana dari permainan Flappy Bird menggunakan Java dan Java Swing. Berikut adalah penjelasan alur dan desain program ini:

1. MenuForm.java:
   - Kelas ini mewakili tampilan menu utama permainan Flappy Bird.
   - Menampilkan latar belakang dan tombol "Start Game" di tengah layar.
   - Tombol "Start Game" akan memulai permainan ketika diklik.

2. FlappyBird.java:
   - Kelas ini mewakili permainan Flappy Bird itu sendiri.
   - Menggunakan JPanel sebagai tempat permainan.
   - Mengatur logika permainan seperti gerakan burung, penempatan pipa, deteksi tabrakan, dan skor.
   - Menangani input dari pemain untuk menggerakkan burung.

3. Player.java:
   - Kelas ini merepresentasikan pemain (burung) dalam permainan.
   - Menyimpan atribut posisi, ukuran, gambar, dan kecepatan player.

4. Pipe.java:
   - Kelas ini merepresentasikan pipa dalam permainan.
   - Menyimpan atribut posisi, ukuran, gambar, dan kecepatan pipa.
   - Mengatur pipa-pipa yang muncul di layar.

5. App.java:
   - Kelas utama yang berisi method main untuk menjalankan program.
   - Membuat instance dari MenuForm untuk menampilkan menu awal.

Program ini merupakan game Flappy Bird, saat menjalankan program akan muncul halaman awal dan muncul tombol "Start Game", apabila diklik maka akan masuk ke game. Saat masuk ke game, kemudian mainkan dengan klik space pada keyboard dan menghindari pipa yang ada. jika melewati setiap pasangan pipa, maka akan mendapatkan poin (+1) dan skor akan muncul di atas. apabila player mengenai pipa maka akan muncul pop up game over dan tertulis skor yang diraih, selain itu ada pertanyaan apakah ingin bermain lagi atau tidak. apabila klik "yes" maka akan kembali ke halaman game, namun apabila klik "no" maka akan keluar dari program. lalu, pada screen record saat bermain dan tiba tiba skor ke reset dan mulai dari awal lagi itu saat klik R pada keyboard hal itu karena terjadi restart game dan mulai lagi dari awal

Dokumentasi
![Screenshot 2024-04-30 193228](https://github.com/faayyaa10/LP7DPBO2024C2/assets/114636102/5a05e126-ecf6-4f12-b1ad-5db48a0d1f1a)
![Screenshot 2024-04-30 200713](https://github.com/faayyaa10/LP7DPBO2024C2/assets/114636102/eed2e643-c87a-41ce-953e-7ef6ce6ca146)
![Screenshot (84)](https://github.com/faayyaa10/LP7DPBO2024C2/assets/114636102/119f376f-5426-4980-a58c-113e425ed9de)




