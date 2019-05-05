export interface ISearch {
    id?: number;
}

export class Search implements ISearch {
    constructor(public id?: number) {}
}
