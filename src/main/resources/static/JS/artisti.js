function redirectToArtistPage(artistId) {
    console.log('Redirecting to artist page with ID:', artistId);
    window.location.href = '/artist/' + artistId;
}
function redirectToArtistPageAdmin(artistId) {
        window.location.href = '/admin/artist/' + artistId;
    }