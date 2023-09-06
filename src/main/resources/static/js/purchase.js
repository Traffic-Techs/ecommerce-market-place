const saleInput = document.getElementById("sale");

if (saleInput.value === "false") {
    saleInput.value = "X";
} else if (saleInput.value === "true") {
    saleInput.value = "O";
}

function purchaseOrder() {
    const data = {
        product: product,
        quantity: quantity
    };

    fetch('/api/orders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(result => {
            if (result.statusCode === 202) {
                alert("주문이 성공적으로 처리되었습니다.");
                window.location.href = `/web/products/${product.id}`;
            } else if (result.statusCode === 400) {
                alert(result.statusCode);
                alert("매진 되었습니다.");
                window.location.href = `/web/products/${product.id}`;
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}