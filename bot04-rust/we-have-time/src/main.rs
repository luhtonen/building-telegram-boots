use chrono::{DateTime, Local, Utc};

// to run execute following command:
// cargo run --bin main1
fn main() {
    let utc: DateTime<Utc> = Utc::now();
    println!("UTC:   {}", utc);
    let local = Local::now();
    println!("Local: {}", local);
}
