document.addEventListener("DOMContentLoaded", () => {
    const projectId = document.querySelector("body").getAttribute("data-project-id");

    // メンバー編集モーダル
    const memberModal = document.getElementById("editMemberModal");
    memberModal.addEventListener("show.bs.modal", event => {
        const button = event.relatedTarget;
        const id = button.getAttribute("data-id");
        const name = button.getAttribute("data-name");

        document.getElementById("editMemberName").value = name;
        document.getElementById("editMemberForm").action = `/projects/${projectId}/members/${id}/edit`;
        document.getElementById("deleteMemberForm").action = `/projects/${projectId}/members/${id}/delete`;
    });

    document.getElementById("deleteMemberBtn").addEventListener("click", () => {
        if (confirm("本当に削除しますか？")) {
            document.getElementById("deleteMemberForm").submit();
        }
    });

    // 支払い編集モーダル
    const expenseModal = document.getElementById("editExpenseModal");
    expenseModal.addEventListener("show.bs.modal", event => {
        const button = event.relatedTarget;
        const id = button.getAttribute("data-id");
        const amount = button.getAttribute("data-amount");
        const desc = button.getAttribute("data-desc");
        const payer = button.getAttribute("data-payer");

        document.getElementById("editExpenseAmount").value = amount;
        document.getElementById("editExpenseDesc").value = desc;
        document.getElementById("editExpensePayer").value = payer;

        document.getElementById("editExpenseForm").action = `/projects/${projectId}/expenses/${id}/edit`;
        document.getElementById("deleteExpenseForm").action = `/projects/${projectId}/expenses/${id}/delete`;
    });

    document.getElementById("deleteExpenseBtn").addEventListener("click", () => {
        if (confirm("本当に削除しますか？")) {
            document.getElementById("deleteExpenseForm").submit();
        }
    });
});
