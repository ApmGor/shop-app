db = db.getSiblingDB("product_db");
db.createUser({
    user: "product_user",
    pwd: "product_pwd",
    roles: [
        {
            role: "readWrite",
            db: "product_db"
        }
    ]
});
db.createCollection("products");
db.products.insertMany([
    {
        description: "Philips TV",
        price: 750
    },
    {
        _id: "5",
        description: "XBOX One",
        price: 500
    },
    {
        description: "Samsung SSD",
        price: 150
    },
    {
        description: "Bosh Washer BW567",
        price: 300
    }
]);