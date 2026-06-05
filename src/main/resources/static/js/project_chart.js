document.addEventListener("DOMContentLoaded", () => {

    const canvas = document.getElementById("paidChart");
    if (!canvas) return;

    if (typeof paidLabels === "undefined" || typeof paidValues === "undefined") {
        console.error("paidLabels / paidValues が読み込めていません");
        return;
    }

    //ラベルと値をまとめてソート
    let combined = paidLabels.map((name, i) => ({ name, value: paidValues[i] }));
    combined.sort((a, b) => b.value - a.value);

    const sortedLabelsWithAmount = combined.map(item => `${item.name}（${item.value}円）`);
    const sortedValues = combined.map(item => item.value);

    //色を人数分生成
    function generateColors(n) {
        const colors = [];
        for (let i = 0; i < n; i++) {
            const hue = (i * 360 / n) % 360;
            colors.push(`hsl(${hue}, 70%, 60%)`);
        }
        return colors;
    }

    //グラフ描画
    new Chart(canvas, {
        type: 'pie',
        data: {
            labels: sortedLabelsWithAmount,
            datasets: [{
                data: sortedValues,
                backgroundColor: generateColors(sortedValues.length),
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });

    //サイズ固定
    canvas.parentElement.style.width = '300px';
    canvas.parentElement.style.height = '300px';
});
