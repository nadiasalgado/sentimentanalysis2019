export interface ISearch {
    id?: number;
    search?: string;
}

export class Search implements ISearch {
    constructor(public id?: number, public search?: string) {}
}
