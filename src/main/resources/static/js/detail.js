const productAmount = `${product.amount}`; // 백엔드에서 받아온 상품의 수량 정보
const orderAmount = `${order}`;

// 수량 옵션을 동적으로 생성하는 함수
function generateQuantityOptions() {
    const quantitySelect = document.getElementById("quantity");

    if (productAmount - orderAmount == 0) {
        const option = document.createElement("option");
        option.value = 0;
        option.textContent = "매진";
        quantitySelect.appendChild(option);
    } else {
        for (let i = productAmount - orderAmount; i >= 1; i--) {
            const option = document.createElement("option");
            option.value = i;
            option.textContent = i + "개";
            quantitySelect.appendChild(option);
        }
    }
}

// 페이지 로드 시 수량 옵션을 생성합니다.
window.onload = generateQuantityOptions;

function addToCart() {
    const quantityString = document.getElementById("quantity").value;
    const quantity = parseInt(quantityString, 10); // 10진수로 변환

    const data = {
        product: product,
        quantity: quantity
    };

    if (productAmount - orderAmount == 0) {
        alert("제품이 매진되었습니다.");
    } else {
        fetch('/addToCart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then(response => response.text())
            .then(data => {
                // 서버로부터 받은 응답 처리
                window.location.href = "/purchase"; // purchase 페이지로 이동
            });
    }

}