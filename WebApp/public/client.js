//Variables to contain differenet html elements
let broker1;
let broker2;
let broker3;
let rate1;
let rate2;
let rate3;
let link1;
let link2;
let link3;
let country;
let countryName1;
let countryName2;
let countryName3;
let i;
let available;
let success;

//Get pointers to parts of the DOM after the page has loaded.
function init() {
    //to clear all exisiting values
    document.getElementById("broker1").value = "";
    broker1 = document.getElementById("broker1");
    broker2 = document.getElementById("broker2");
    broker3 = document.getElementById("broker3");

    rate1 = document.getElementById("rate1");
    rate2 = document.getElementById("rate2");
    rate3 = document.getElementById("rate3");

    link1 = document.getElementById("link1");
    link2 = document.getElementById("link2");
    link3 = document.getElementById("link3");

    countryName1 = document.getElementById("countryName1");
    countryName2 = document.getElementById("countryName2");
    countryName3 = document.getElementById("countryName3");

    country = document.getElementById("country").value;
    document.getElementById("country").value = "";
    loadCurrencies();

    success = document.getElementById("success");

    //To Stop refresshing page when searched.
    $("#prospects_form").submit(function (e) {
        e.preventDefault();
    });

    i = 0;

    available = false;
}

/* Loads current users and adds them to the page. */
function loadCurrencies() {
    //Set up XMLHttpRequest
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = () => {//Called when data returns from server
        if (xhttp.readyState == 4 && xhttp.status == 200) {

            //Convert JSON to a JavaScript object
            let currencyList = JSON.parse(xhttp.responseText);

            // extracting data from js object
            currencyList = currencyList["data"];
            console.log("Respone " + JSON.stringify(currencyList));

            // if rates are available
            if (currencyList.length > i && country != "") {

                $('#success').show().delay(2000).fadeOut();

            }//If rates are not available. 
            else if (country != "" || currencyList[0].country == "") {
                $('#failure').show().delay(2000).fadeOut();

            }

            //Return if no country found
            if (currencyList.length === 0) {

                return;
            }
            else {

                //Updating Broker'Name
                broker1.innerHTML = currencyList[0].broker;
                broker2.innerHTML = currencyList[1].broker;
                broker3.innerHTML = currencyList[2].broker;

                //Upating rate and curency
                rate1.innerHTML = currencyList[0].rate + " " + currencyList[0].currency;
                rate2.innerHTML = currencyList[1].rate + " " + currencyList[1].currency;
                rate3.innerHTML = currencyList[2].rate + " " + currencyList[2].currency;

                //Updating country name
                countryName1.innerHTML = currencyList[0].country;
                countryName2.innerHTML = currencyList[1].country;
                countryName3.innerHTML = currencyList[2].country;


                //Updating link
                link1.getAttribute("href");
                link1.setAttribute("href", currencyList[0].url);
                link2.getAttribute("href");
                link2.setAttribute("href", currencyList[1].url);
                link3.getAttribute("href");
                link3.setAttribute("href", currencyList[2].url);

            }
        }

    };

    //Sending country name to request rates.
    xhttp.open("GET", "/currencyCompare?country=" + country, true);
    xhttp.send();
}


