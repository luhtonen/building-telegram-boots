import std.concurrency : spawn, thisTid;
import std.stdio : writeln;

void worker()
{
    static int threadState = 0;
    writeln("Thread ", thisTid, ": My state = ", threadState++);
    if (threadState < 5) worker();
}

// to run execute following command:
// dmd -run threads/threads2.d
void main()
{
    int TOTAL_WORKERS = 5;
    for (int i = 0; i < TOTAL_WORKERS; ++i) {
        spawn(&worker);
    }
}
