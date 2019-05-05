import { Moment } from 'moment';

export const enum Searchlang {
    ENGLISH = 'ENGLISH',
    FRENCH = 'FRENCH',
    SPANISH = 'SPANISH',
    DUTCH = 'DUTCH'
}

export interface ISearch {
    id?: number;
    search?: string;
    lastSearch?: Moment;
    slastSearch?: Moment;
    language?: Searchlang;
}

export class Search implements ISearch {
    constructor(
        public id?: number,
        public search?: string,
        public lastSearch?: Moment,
        public slastSearch?: Moment,
        public language?: Searchlang
    ) {}
}
