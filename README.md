# Android demo app for Nexmo's Verify Two Factor Authentication API

This is a companion demo to the blog post [Add Two Factor Authentication to Android apps with Nexmo's Verify API](https://www.nexmo.com/blog/2018/05/09/add-two-factor-authentication-to-android-apps-with-nexmos-verify-api-dr)

## Prerequisites

To run this app you'll need to set up a proxy API server. Read more in the [Nexmo Verify API implementation guide](https://www.nexmo.com/blog/2018/05/09/nexmo-verify-api-implementation-guide-dr) blog post.

## Set up

There are two branches `finished` and `getting-started.` If you're following along with the blog post, start at `getting-started` follow along with the blog post.

If you'd like to see the finished product, switch to the `finished` branch

## Running the demo

- Download the project, open it in Android Studio and then run the app!
- You'll want to change the `baseUrl` in [`VerifyUtil.java`](https://github.com/nexmo-community/verify-android-example/blob/finished/app/src/main/java/com/nexmo/twofactorauth/utils/VerifyUtil.java#L23)
