'use strict';

const Student = require('../models/student');

const getAllStudents = async(req, res, next) => {
    try {

        res.send([{ "name": "haha" }]);

    } catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getAllStudents,
}