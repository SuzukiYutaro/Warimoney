const editProjectModal = document.getElementById('editProjectModal');
editProjectModal.addEventListener('show.bs.modal', event => {
    const button = event.relatedTarget;
    const projectId = button.getAttribute('data-id');
    const projectName = button.getAttribute('data-name');

    document.getElementById('editProjectName').value = projectName;
    document.getElementById('editProjectForm').action = `/projects/${projectId}/edit`;
    document.getElementById('deleteProjectForm').action = `/projects/${projectId}/delete`;
});

document.getElementById('deleteProjectBtn').addEventListener('click', () => {
    if (confirm('本当にこのプロジェクトを削除しますか？')) {
        document.getElementById('deleteProjectForm').submit();
    }
});