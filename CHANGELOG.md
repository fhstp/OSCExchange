# OSCExchange Changelog

## 2.2.0 - 2021-June-25

### Changed

- Make OSCAddress and OSCDevicePair parcelable

## 2.1.0 - 2021-June-17

### Added

- Added request timeout logic for receive-requests

## 2.0.0 - 2021-June-16

### Changed

- Changed the syntax for building OSCExchanges
- Error handlers are now attached to each request instead to the whole exchange
- The is no completion handler anymore, instead add success/receive handlers to the last request in the exchange

## 1.0.0 - 2021-May-19

### Add

- Add logic for building and running OSCExchanges
- Add tutorial and guides