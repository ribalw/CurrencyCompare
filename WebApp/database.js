
//Import the mysql module
let mysql = require('mysql');

//Create a connection object with the user details
let connectionPool = mysql.createPool({
    connectionLimit: 1,
    host: "localhost",
    user: "root",
    password: "",
    database: "currency_compare",
    debug: false
});

/** Returns a promise to get the total number of products */
exports.getProductCount = async () => {
    //Build SQL query
    let sql = "SELECT COUNT(*) FROM comparison";

    //Execute promise to run query
    let result = await executeQuery(sql);

    //Extract the data we need from the result
    return result[0]["COUNT(*)"];

}


/** Returns all the products with optional pagination */
exports.getCurrency = async (numItems, offset, country) => {
    let sql = " SELECT broker.name AS broker, currency.country ,currency.name as currency, comparison.rate, comparison.url from comparison " +
        "JOIN broker	 ON (broker.id = comparison.broker_Id)" +
        "JOIN currency ON (currency.id = comparison.currency_Id)";

    if (country !== undefined) {
        sql += " WHERE country = '" + country + "'";
    }

    //Limit the number of results returned, if this has been specified in the query string
    if (numItems !== undefined && offset !== undefined) {
        sql += " ORDER BY comparison.id LIMIT " + numItems + " OFFSET " + offset;
    }

    //Return promise to run query
    return executeQuery(sql);
}


/** Returns a promise to get details about a single product */
exports.getCurrencyById = async (id) => {
    let sql = "SELECT broker.name AS broker, currency.country ,currency.name as currency, comparison.rate, comparison.url from comparison" +
        " JOIN broker	 ON (broker.id = comparison.broker_Id)" +
        "JOIN currency ON (currency.id = comparison.currency_Id)" +
        " WHERE comparison.id = " + id;
    return executeQuery(sql);
}

/** Wraps connection pool query in a promise and returns the promise */
async function executeQuery(sql) {
    //Wrap query around promise
    let queryPromise = new Promise((resolve, reject) => {
        //Execute the query
        connectionPool.query(sql, function (err, result) {
            //Check for errors
            if (err) {
                //Reject promise if there are errors
                reject(err);
            }

            //Resole promise with data from database.
            resolve(result);
        });
    });

    //Return promise
    return queryPromise;
}



