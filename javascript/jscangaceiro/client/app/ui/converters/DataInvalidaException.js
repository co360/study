// client/app/ui/converters/DataInvalidaException.js

class DataInvalidaException extends Error {

    constructor() {
        super('A data deve estar no formato dd/mm/aaaa');
    }
}
