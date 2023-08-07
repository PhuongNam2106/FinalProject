function kiemtra(e)
{
    e.preventDefault();
    var taikhoan = document.getElementById('account').value;
    var password = document.getElementById('password').value;
    if(taikhoan == 'admin' && password=='nam123456' )
    {
        window.location.href = '../dashboard/index.html';
    }
    else
    alert('Sai tài khoản hoặc mật khẩu');
}