/*
 document.addEventListener('DOMContentLoaded', function() {
 document.querySelectorAll('.edit-card').forEach(item => {
 item.addEventListener('click', event => {
 const cardId = item.getAttribute('data-card-id');
 fetch('/card/edit/' + cardId)
 .then(response => response.json())
 .then(data => {
 // Populate form fields with card data
 console.log(data);
 document.getElementById('tTextbox').value = data.title;
 document.getElementById('tSyn').value = data.synopsis;
 document.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
 checkbox.checked = false;
 });
 if (data.genres) {
 const genresArray = data.genres.split(',').map(genre => genre.trim());
 genresArray.forEach(genre => {
 document.getElementById(genre.toLowerCase()).checked = true; // Ensure genre IDs are lowercase
 });
 }
 document.getElementById('imagePath').value = data.imagePath;
 })
 .catch(error => console.error('Error:', error));
 });
 });
 }); */

document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.edit-card').forEach(item => {
        item.addEventListener('click', event => {
            const cardId = item.getAttribute('data-card-id');
            const userId = item.getAttribute('user-id');
            window.location.href = '/creator/' + userId + '/edit/' + cardId;
        });
    });
});

  