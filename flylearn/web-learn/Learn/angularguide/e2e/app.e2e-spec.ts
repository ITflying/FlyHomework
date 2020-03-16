import { AngularguidePage } from './app.po';

describe('angularguide App', () => {
  let page: AngularguidePage;

  beforeEach(() => {
    page = new AngularguidePage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
