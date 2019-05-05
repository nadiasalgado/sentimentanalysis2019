export interface IUserProfile {
    id?: number;
}

export class UserProfile implements IUserProfile {
    constructor(public id?: number) {}
}
