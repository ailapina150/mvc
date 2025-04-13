function confirmDelete() {
    if (confirm("Вы действительно хотите удалить это резюме? Это действие необратимо.")) {
        return true;
    } else {
        return false;
    }
}