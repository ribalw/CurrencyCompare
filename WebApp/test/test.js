
//Database code defined in external file
let db = require('../database');

//Built in Node.js assertions
const assert = require("assert");

//Chai library for HTTP requests and more sophisticated assertions
let chai = require('chai');
let chaiHttp = require('chai-http');
let should = chai.should();
chai.use(chaiHttp);
let expect = chai.expect;

//Import server
let server = require('../server');

//Wrapper for all database tests
describe('DataBase', function () {

    //Mocha test for getProductCount method in Database.
    describe('#getProductCount', function () {
        it('should return the total number of currencies in the database', async () => {


            //getAllCurrencies is the callback function in database object. Change it so that we can finish the test.
            let result = await db.getProductCount();
            expect(result).to.equal(236);

        });
    });

    //Mocha test for getProductByID method in Database.
    describe('#getProductById', function () {
        it('should return the currency with id (1) from the database', async () => {


            //getAllCurrencies is the callback function in database object. Change it so that we can finish the test.
            let result = await db.getCurrencyById(1);
            expect(result[0].country).to.equal("Albania");

        });
    });
    //Mocha test for getProductByID method in Database.
    describe('#getProductByCOuntry', function () {
        it('should return the currency for EUR from the database', async () => {


            //getAllCurrencies is the callback function in database object. Change it so that we can finish the test.
            let result = await db.getCurrency("Germany");
            expect(result[0].currency).to.equal("EUR");

        });
    });
});

//Wrapper for all API  tests
describe('RESTfulAPI', function () {

    //Mocha Chai test of RESTful Web Service 1
    describe('/GET all currencies', () => {
        it('should GET all the currencies', (done) => {
            chai.request(server)
                .get('/currencyCompare')
                .end((err, res) => {
                    res.should.have.status(200);
                    done();
                });
        });
    });

    //Mocha Chai test of RESTful Web Service 2
    describe('/GET currency for specific country', () => {
        it('should GET currencies for specific country', (done) => {
            chai.request(server)
                .get('/currencyCompare?country=Pakistan')
                .end((err, res) => {
                    res.should.have.status(200);
                    done();
                });
        });
    });

});

