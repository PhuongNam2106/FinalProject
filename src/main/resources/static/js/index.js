$(".delete").click(function() {
	return confirm("Do you want to delete this fresher?")
})

/*
function checkValidateField(idErrMsg, minLength, maxLength, text, nameField) {
    if (text.length < minLength || text.length > maxLength) {
        textErr = `${nameField} must be not empty `;
        document.getElementById(idErrMsg).hidden = false;
        document.getElementById(idErrMsg).innerHTML = textErr;
        return false;
    } else {
        document.getElementById(idErrMsg).hidden = true;
        return true;
    }

}*/

/*function validate()
{
    var fresherName = document.getElementById("fresherName").value;
    if(fresherName=="")
    {
        document.getElementById("fresherNameErr").hidden=false;
        document.getElementById("fresherNameErr").innerHTML='Name must not empty';
            return false;
    }
    else
    {
    document.getElementById("fresherNameErr").hidden=false;
    return true;}
}

document.querySelector("#form-add").addEventListener("submit", (e) => {
    e.preventDefault();
    console.log(validate());

    if (validate()) {
         e.currentTarget.submit();
    }
});*/
