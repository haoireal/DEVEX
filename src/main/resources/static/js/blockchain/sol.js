
const walletAddress = "A5jsvxmGpe2sj3G1aBv6iiF5jNQ9nfujTJhzSuJ74ozd";
const getBalance = async function () {
    try {
        // Kết nối đến mạng lưới Solana
        const connection = new solanaWeb3.Connection(
            "https://api.devnet.solana.com",
            "confirmed"
        );
        // Chuyển địa chỉ ví từ dạng chuỗi thành đối tượng PublicKey
        const publicKey = new solanaWeb3.PublicKey(walletAddress);
        const balance = await connection.getBalance(publicKey);
        console.log(`Balance: ${balance / solanaWeb3.LAMPORTS_PER_SOL} SOL, Ví Sol: ${walletAddress}`);
        document.getElementById("balance").innerText = `Balance: ${(balance / solanaWeb3.LAMPORTS_PER_SOL).toFixed(5)
            } SOL`;  // Chia cho LAMPORTS_PER_SOL để chuyển từ lamports sang SOL và làm tròn đến hai chữ số thập phân
    } catch (error) {
        console.error(error);
    }
};

// Gọi hàm để kiểm tra số dư khi trang được tải
console.log(getBalance());


