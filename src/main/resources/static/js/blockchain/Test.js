// const walletAddress = "A5jsvxmGpe2sj3G1aBv6iiF5jNQ9nfujTJhzSuJ74ozd";
// console.log("Wallet Address: " + walletAddress);
import { PublicKey, Connection } from ('@solana/web3.js');
// const web3 = require("@solana/web3.js");

const getBalance = async function () {
    try {
        // Kết nối đến mạng lưới Solana
        const connection = new Connection(
            "https://api.devnet.solana.com",
            "confirmed"
        );

        // Chuyển địa chỉ ví từ dạng chuỗi thành đối tượng PublicKey
        const publicKey = new PublicKey(walletAddress);

        // Lấy số dư của ví
        const balance = await connection.getBalance(publicKey);

        console.log(`Balance: ${balance / 1e9} SOL, Ví Sol: ${walletAddress}`);
        // Hiển thị số dư trong trang web
        // document.getElementById("balance").innerText = `Balance: ${
        //   (balance / 1e9).toFixed(2)
        // } SOL`;
        // Chia cho 1e9 để chuyển từ lamports sang SOL và làm tròn đến hai chữ số thập phân
    } catch (error) {
        console.error("Lỗi khi lấy số dư:", error);
    }
};

// Gọi hàm để kiểm tra số dư khi trang được tải
getBalance();
