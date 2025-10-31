# TP6DPBO2425C1
## Janji
Saya Dzaka Musyaffa Hidayat dengan NIM 2404913 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Program  
Program implementasi game Flappy Bird yang dibuat menggunakan Java Swing GUI.  

## Controls  
Space - Lompat Burung  
R - Restart Game  
M - Main Menu  

## Desain Program  
- **App.java**  
  Class utama untuk menjalankan aplikasi Flappy Bird.
- **View.java**  
  Class yang berfungsi untuk menggambar (_draw graphics_) semua elemen game (burung, pipa, background, skor, main menu, dan teks di window app.  
- **Logic.java**  
  Class yang berisi semua logika dan fungsi dari game. 
- **Pipe.java**  
  Class yang merepresentasikan objek pipa yang berisikan atribut posisi, dimensi, gambar, state terlewati (``passed``)  
- **Player.java**  
  Class yang merepresentasikan objek player yang berisikan atribut posisi, dimensi, gambar, dan kecepatan vertikal dalam axis Y (``velocityY``)

## Alur Program  
- **Pembuatan Jendela**  
  Sebuah JFrame (jendela utama) dibuat dengan judul "Flappy Bird". Ukurannya diatur ke 360x640, dan tidak bisa diubah ukurannya.
- **Inisialisasi Logic**  
  Sebuah objek Logic dibuat. Saat Logic dibuat (di dalam konstruktornya), ia memuat gambar untuk burung ``birdImage`` dan pipa ``lowerPipeImage``, ``upperPipeImage``.  
  Ia membuat objek ``Player`` baru.  
  Ia membuat ``ArrayList`` kosong untuk menampung pipa (pipes).  
  Ia menyiapkan dua Timer:  
  ``gameLoop``: Timer utama yang berjalan setiap 1/60 detik (sekitar 60 FPS) dan memanggil metode ``actionPerformed`` di Logic. Timer ini langsung dimulai.  
  ``pipesCooldown``: Timer untuk memunculkan pipa baru setiap 1,5 detik. Timer ini dihentikan (stop()) saat awal.  
- **Inisialisasi View**  
  Sebuah objek View (yang merupakan JPanel) dibuat, dan objek logic yang baru saja dibuat diteruskan ke dalamnya. Saat View dibuat (di dalam konstruktornya), ia mengatur ukurannya.  
  Ia memuat gambar latar belakang (background.png) dan font khusus (pixel.ttf).  
  Ia memanggil ``createMenuButtons()``, yang membuat tombol "Start Game" dan "Exit Game".  
  Ia mendaftarkan logic sebagai KeyListener-nya, sehingga Logic akan menerima input keyboard.  
- **Menghubungkan Logic dan View**  
  logic.setView(view) dipanggil untuk memberi tahu Logic panel View mana yang harus digambar ulang.  
- **Menampilkan Jendela**  
  View ditambahkan ke JFrame, jendela ditampilkan ``setVisible(true)``, dan ``view.requestFocus()`` dipanggil agar panel View fokus dan bisa menerima input keyboard.

## Dokumentasi Program  
https://github.com/user-attachments/assets/80b6e6c3-67b7-498e-8bbf-a2c514da7be4


