document.addEventListener('DOMContentLoaded', () => {
    const bookListElement = document.getElementById('book-list');

    fetch('/book/get-all')
        .then(response => response.json())
        .then(data => {
            data.forEach(book => {
                const li = document.createElement('li');
                
                // Kitap detaylarını gösteren bir div oluşturma
                const bookDetails = `
                    <div class="book-details">
                        <strong>İsim:</strong> ${book.name}<br>
                        <strong>Yazar:</strong> ${book.author}<br>
                        <strong>ISBN:</strong> ${book.isbn}<br>
                        <strong>Fiyat:</strong> ${book.price} TL<br>
                        <strong>Sayfa Sayısı:</strong> ${book.pageNumber}<br>
                        <strong>Yayın Tarihi:</strong> ${book.publicationDate}<br>
                        <strong>Yayıncı:</strong> ${book.publisher}<br>
                        <strong>Stok:</strong> ${book.stockNumber} adet
                    </div>
                `;
                
                li.innerHTML = bookDetails;
                bookListElement.appendChild(li);
            });
        })
        .catch(error => console.error('Error fetching book data:', error));
});
