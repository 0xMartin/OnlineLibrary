exports = async function (changeEvent) {
    const { operationType, ns, fullDocument} = changeEvent;

    // jen pokud jde o DELETE operaci
    if(operationType !== "DELETE") {
        return;
    }

    if(ns.coll !== "Borrows") {
        return;
    }

    //var book_id = fullDocument.book_id;
    console.log("borrow deleted");
};