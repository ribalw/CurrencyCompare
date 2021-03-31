//Import the express , url and body parser modules
const express = require('express');
const url = require("url");
const bodyParser = require('body-parser');

const db = require('./database');

//Status codes defined in external file
require('./http_status.js');

//The express module is a function. When it is executed it returns an app object
var app = express();
app.use(bodyParser.json());

//Set up express to serve static files from the directory called 'public'
app.use(express.static('public'));

//Set up the application to handle GET requests sent to the user path
app.get('/currencyCompare/*', handleProductGet);//Subfolders
app.get('/currencyCompare', handleProductGet);

//Start the app listening on port 8080
app.listen(8080);


/* Handles GET request sent to products path
   Processes path and query string and calls appropriate functions to
   return the data. */
async function handleProductGet(request, response) {
    //Parse the URL
    let urlObj = url.parse(request.url, true);

    //Extract object containing queries from URL object.
    let queries = urlObj.query;

    //Get the pagination properties if they have been set. Will be  undefined if not set.
    let numItems = queries['num_items'];
    let offset = queries['offset'];
    let country = queries['country'];

    //Split the path of the request into its components
    let pathArray = urlObj.pathname.split("/");

    //Get the last part of the path
    let pathEnd = pathArray[pathArray.length - 1];

    //If path ends with 'products' we return all products
    try {
        if (pathEnd === 'currencyCompare') {
            //Get the total number of products for pagination
            let prodCount = await db.getProductCount();

            //Get the products
            let products = await db.getCurrency(numItems, offset, country);

            //Combine into a single object and send back to client
            let returnObj = {
                count: prodCount,
                data: products
            }
            response.json(returnObj);
        }

        //If the last part of the path is a valid product id, return data about that product
        let regEx = new RegExp('^[0-9]+$');//RegEx returns true if string is all digits.
        if (regEx.test(pathEnd)) {
            let product = await db.getCurrencyById(pathEnd);
            response.json(product);
        }
    }
    catch (ex) {
        response.status(HTTP_STATUS.NOT_FOUND);
        response.send("{error: '" + JSON.stringify(ex) + "', url: " + request.url + "}");
    }
}
// ecporting express app
module.exports = app;

